package com.gmcc.hdp.demo.oozie;

import com.gmcc.hdp.demo.util.HDPSampleConfiguration;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.oozie.client.AuthOozieClient;
import org.apache.oozie.client.OozieClientException;
import org.apache.oozie.client.WorkflowJob;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Created by makun on 16/8/9.
 */
public class OozieKrbDemo {
    private AuthOozieClient oozieClient =null;

    public OozieKrbDemo(AuthOozieClient oozieClient){
        this.oozieClient=oozieClient;
    }

    /**
     * @return - jobId
     * @throws OozieClientException
     */
    public String startJob(Properties properties)
            throws OozieClientException {
        Properties configuration = oozieClient.createConfiguration();

        Enumeration<?> enumeration = properties.propertyNames();
        while (enumeration.hasMoreElements()) {
            Object element = enumeration.nextElement();
            configuration.setProperty(element.toString(), properties.getProperty(element.toString()).toString());
        }

        return oozieClient.run(configuration);
    }

    public static void main(String[] args) {
        try {
            Configuration conf = new Configuration();
            UserGroupInformation.setConfiguration(conf);
            UserGroupInformation.loginUserFromKeytab(args[0], args[1]);
            String cmd = new StringBuilder("kinit -kt ").append(args[1]).append(" -p ").append(args[0]).toString();
            Process process = Runtime.getRuntime().exec(cmd);
            process.waitFor();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        System.setProperty("java.security.krb5.conf","/etc/krb5.conf");
        try {
            String configPath=args[3];
            HDPSampleConfiguration hdpSampleConfiguration=null;
            if(configPath.isEmpty()){
                System.out.println("please input config path");
                return;
            }else{
                hdpSampleConfiguration=new HDPSampleConfiguration(configPath);
            }

            AuthOozieClient oozieClient= new AuthOozieClient(HDPSampleConfiguration.OOZIE_URL,"KERBEROS");
            OozieKrbDemo client = new OozieKrbDemo(oozieClient);

            Properties properties = new Properties();
            properties.load(new FileInputStream(new File(args[2])));
            properties.setProperty("oozie.authentication.type", "Kerberos");


            String jobId = client.startJob(properties);
            System.out.println("jobId: " + jobId);
            WorkflowJob jobInfo = client.oozieClient.getJobInfo(jobId);
            System.out.println("job url: " + jobInfo.getConsoleUrl());

            while (true) {
                Thread.sleep(1000L);
                WorkflowJob.Status status = client.oozieClient.getJobInfo(jobId).getStatus();
                if (status == WorkflowJob.Status.SUCCEEDED || status == WorkflowJob.Status.FAILED
                        || status == WorkflowJob.Status.KILLED) {
                    System.out.println("Job finish with status: " + status);
                    break;
                } else {
                    System.out.println("job running: " + jobInfo.getStatus());
                }
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}

package com.gmcc.hdp.demo.oozie;

import com.gmcc.hdp.demo.util.HDPSampleConfiguration;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.oozie.client.AuthOozieClient;
import org.apache.oozie.client.OozieClient;
import org.apache.oozie.client.OozieClientException;
import org.apache.oozie.client.WorkflowJob;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Created by makun on 16/8/10.
 */
public class OozieDemo {
    private OozieClient oozieClient = new OozieClient(HDPSampleConfiguration.OOZIE_URL);
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
            OozieDemo client = new OozieDemo();

            Properties properties = new Properties();
            properties.load(new FileInputStream(new File(args[0])));

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

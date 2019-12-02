package com.dalididilo.kafka.esinhai.kafkainit.init;


import com.esinhai.kafkainit.entity.KafkaServerYmlBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.common.Node;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Kafka 中央控制台
 * @author zwb_dev
 * @date 2019-12-01 16:52:11
 * todo 1、查询所有的Topic  queryTopics
 * todo 2、创建Topic createTopic
 * todo 3、删除Topic deleteTopic
 * todo 4、查询集群信息 queryClusterNode
 * todo 5、增加分区 addPartitions
 * todo 6、查询具体的Topic describeTopics
 */
@Component
public class KafkaInit {

    private AdminClient adminClient;

    /**
     * 单例模式。
     */
    private volatile KafkaInit instance = null;

    private KafkaInit(){

    }

    /**
     * 带构造器的初始化KafkaAdminClient
     * @param prop
     */
    private KafkaInit(KafkaServerYmlBean prop){
        adminClient = AdminClient.create(prop.initProp());
    }

    /**
     * 暴露出kafka控制Client 对应方法去实现。
     * @return
     */
    public KafkaInit getInstance(KafkaServerYmlBean prop) {
        if(instance == null){
            synchronized (KafkaInit.class){
                if(instance == null){
                    instance = new KafkaInit(prop);
                }
            }
        }
        return instance;
    }

    /**
     * kafka集群的Node信息。
     * @return kafka服务器集群的服务器配置
     * @throws Exception
     */
    public List<String> queryClusterNode() throws Exception {
        Collection<Node> nodes = adminClient.describeCluster().nodes().get();
        List<String> brokerNodes = new ArrayList<>();
        for (Node node : nodes) {
            brokerNodes.add(node.host() + ":" + node.port());
        }
        return brokerNodes;
    }

    /**
     * 查询所有 Topic
     * @return Topic 列表。
     * @throws Exception
     */
    public List<String> queryTopics() throws Exception {

        Map<String, TopicListing> topicListingMap = adminClient.listTopics().namesToListings().get();
        List<String> topics = new ArrayList<>();
        for (Map.Entry<String, TopicListing> stringTopicListingEntry : topicListingMap.entrySet()) {
            topics.add(stringTopicListingEntry.getKey());
        }
        return topics;
    }


    /**
     * 创建一个Topic
     * @param name topic 主题名称
     * @param partitionNum topic 分区数量
     * @param replicationFactorNum 副本因子
     * @return ""则表示创建失败了，否在返回创建完的topicName
     */
    public String createTopic(String name,Integer partitionNum,Short replicationFactorNum){
        CreateTopicsResult result = adminClient
                .createTopics(Arrays.asList(new NewTopic(name, partitionNum, replicationFactorNum)));
        if(result != null){
            return result.values().keySet().iterator().next();
        }
        return "";
    }

    /**
     * 创建多个Topic
     * @param newTopics NewTopic 集合。
     */
    public void createTopics(Collection<NewTopic> newTopics){
        adminClient.createTopics(newTopics);
    }

    /**
     * 为指定的主题Topic添加分区
     * @param topicName 分区名称
     * @param totalCountNum 设置主题Topic的总分区数量。
     */
    public void addPartitions(String topicName,Integer totalCountNum){
        Map<String, NewPartitions> partitionsMap = new HashMap<>(1);
        partitionsMap.put(topicName,NewPartitions.increaseTo(totalCountNum));
        adminClient.createPartitions(partitionsMap);
    }


    /**
     * 查询具体的describe topicName
     * @param topicName
     */
    public void describeTopics(String topicName){
        adminClient.describeTopics(Arrays.asList(topicName));
    }

    /**
     * 删除指定具体的topicName
     * @param topicName
     */
    public void deleteTopic(String topicName){
        adminClient.deleteTopics(Arrays.asList(topicName));
    }
}

package cn.itcast.hotel.config;

/**
 * @autor: 我亦无他，唯手熟尔
 */

import cn.itcast.hotel.constants.MqConstants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqConfig {
  @Bean // 交换机定义, 持久化 durable
  public TopicExchange topicExchange(){
    return new TopicExchange(MqConstants.HOTEL_EXCHANGE, true, false);
  }

  @Bean // 定义 修改队列 queue, 持久化,
  public Queue insertQueue(){
    return new Queue(MqConstants.HOTEL_INSERT_QUEUE, true);
  }

  @Bean // 定义 删除队列 queue, 持久化,
  public Queue deleteQueue(){
    return new Queue(MqConstants.HOTEL_DELETE_QUEUE, true);
  }

  @Bean // 进行绑定, 将 queue 和 exchange 进行绑定, 并且指定 bindkey
  public Binding insertQueueBinding(){
    return BindingBuilder.bind(insertQueue()).to(topicExchange()).with(MqConstants.HOTEL_INSERT_KEY);
  }

  @Bean
  public Binding deleteQueueBinding(){
    return BindingBuilder.bind(deleteQueue()).to(topicExchange()).with(MqConstants.HOTEL_DELETE_KEY);
  }
}

package com.bundledev.feedcollector;

import java.util.Map.Entry;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bundledev.feedcollector.listener.FeedItemWriteListener;
import com.bundledev.feedcollector.persistence.model.BundleItemEntity;
import com.bundledev.feedcollector.persistence.repository.BundleItemJpaRepository;
import com.bundledev.feedcollector.processor.FeedItemProcessor;
import com.bundledev.feedcollector.reader.FeedItemReader;
import com.rometools.rome.feed.synd.SyndEntry;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
	public BundleItemJpaRepository bundleItemJpaRepository;

	public RepositoryItemWriter<BundleItemEntity> writer() {
		RepositoryItemWriter<BundleItemEntity> writer = new RepositoryItemWriter<>();
		writer.setRepository(bundleItemJpaRepository);
		writer.setMethodName("save");
		return writer;
	}

	// tag::jobstep[]
	@Bean
	public Job collectFeed(@Qualifier("step1") Step step1) {
		return jobBuilderFactory.get("collectFeed").start(step1).build();
	}

	@Bean
	public Step step1(FeedItemReader reader, FeedItemProcessor feedProcessor,
			FeedItemWriteListener feedItemWriteListener) {
		return stepBuilderFactory.get("step1").allowStartIfComplete(true)
				.<Entry<String, SyndEntry>, BundleItemEntity>chunk(10).reader(reader).processor(feedProcessor)
				.writer(writer()).listener(feedItemWriteListener).build();
	}
	// end::jobstep[]
}

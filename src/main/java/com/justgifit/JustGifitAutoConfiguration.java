package com.justgifit;

import java.io.File;

import javax.inject.Inject;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.filter.HttpPutFormContentFilter;
import org.springframework.web.filter.RequestContextFilter;

import com.justgifit.service.ConverterService;
import com.justgifit.service.GifEncoderService;
import com.justgifit.service.VideoDecoderService;
import com.madgag.gif.fmsware.AnimatedGifEncoder;

@Configuration
@ConditionalOnClass({ FFmpegFrameGrabber.class, AnimatedGifEncoder.class })
@EnableConfigurationProperties(JustGifitProperties.class)
public class JustGifitAutoConfiguration {

	public JustGifitAutoConfiguration() {
	}

	@Inject
	private JustGifitProperties justGifitProperties;

	@ConditionalOnProperty(prefix = "com.justgifit", name = "create-result-dir")
	public boolean createResultDir() {
		if (!justGifitProperties.getGifLocation().exists()) {
			justGifitProperties.getGifLocation().mkdir();
		}
		return true;
	}

	@Bean
	@ConditionalOnMissingBean(VideoDecoderService.class)
	public VideoDecoderService videoDecoderService() {
		return new VideoDecoderService();
	}

	@Bean
	@ConditionalOnMissingBean(GifEncoderService.class)
	public GifEncoderService gifEncoderService() {
		return new GifEncoderService();
	}

	@Bean
	@ConditionalOnMissingBean(ConverterService.class)
	public ConverterService converterService() {
		return new ConverterService();
	}

	@Configuration
	@ConditionalOnWebApplication
	public static class WebConfiguration {

		@Value("${multipart.location}/gif")
		private String gifLocation;

		@Bean
		@ConditionalOnProperty(prefix = "com.justgifit", name = "optimize")
		public FilterRegistrationBean deRegisterHiddenHttpMethodFilter(HiddenHttpMethodFilter filter) {
			FilterRegistrationBean filterRegBean = new FilterRegistrationBean(filter);
			filterRegBean.setEnabled(false);
			return filterRegBean;
		}

		@Bean
		@ConditionalOnProperty(prefix = "com.justgifit", name = "optimize")
		public FilterRegistrationBean deRegisterHttpPutFormFilter(HttpPutFormContentFilter filter) {
			FilterRegistrationBean filterRegBean = new FilterRegistrationBean(filter);
			filterRegBean.setEnabled(false);
			return filterRegBean;
		}

		@Bean
		@ConditionalOnProperty(prefix = "com.justgifit", name = "optimize")
		public FilterRegistrationBean deRegisterRequestContextFilter(RequestContextFilter filter) {
			FilterRegistrationBean filterRegBean = new FilterRegistrationBean(filter);
			filterRegBean.setEnabled(false);
			return filterRegBean;
		}

	}
}

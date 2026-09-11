package erplite.ia.configs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@Slf4j
public class IaConfigs {

    @Bean(value="ollama")
    @Primary
    public ChatClient ollamaChatClient(ChatClient.Builder builder){
        log.info("Init Ollama Bean");
        return builder.build();
    }

    @Bean(value="gemini")
    public ChatClient geminiChatClient(
            @Value("${gemini.api-key}") String apiKey,
            @Value("${gemini.base-url}") String baseUrl,
            @Value("${gemini.completions-path}") String completionsPath,
            @Value("${gemini.model}") String model,
            @Value("${gemini.temperature}") Double temperature
    ){
        log.info("Init Gemini Bean");

        var openIaApi = OpenAiApi.builder()
                .baseUrl(baseUrl)
                .completionsPath(completionsPath)
                .apiKey(apiKey)
                .build();

        var openIaOptions = OpenAiChatOptions.builder()
                .model(model)
                .temperature(temperature)
                .build();

        var chatModel = OpenAiChatModel.builder()
                .openAiApi(openIaApi)
                .defaultOptions(openIaOptions)
                .build();

        return ChatClient.builder(chatModel).build();
    }
}

package br.com.api.youspeaking.feature.Quiz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import br.com.api.youspeaking.feature.Thirdparties.OpenAI.OpenApiClient;
import br.com.api.youspeaking.feature.Thirdparties.OpenAI.OpenApiService;
import br.com.api.youspeaking.utils.Utils;
import br.com.api.youspeaking.vo.ChatRequestVO;
import br.com.api.youspeaking.vo.MessageVO;
import feign.Feign;

@Service
public class QuizService {

    @Autowired OpenApiService openAIservice;

    public ObjectNode generateQuestion(String login, String language) throws Exception {
        ObjectNode response = Utils.genericJsonSuccess();
        response.put("data", getChatResponse(login, language));
        return response;
    }

    public ObjectNode getChatResponse(String login, String language) throws JsonProcessingException {
        ChatRequestVO chatRequest = new ChatRequestVO();
        List<MessageVO> listaMensagens = new ArrayList();
        String prompt = "Crie 5 perguntas de alternativas para medir o nível de ${idiomaAprendizado} de uma pessoa de língua matriz ${idiomaMae} com retorno em json em camel case indicando se a alternativa está certa ou errada no próprio objeto de alternativa sem indicar a letra da resposta";
        prompt = prompt.replace("${idiomaAprendizado}", "EN");
        prompt = prompt.replace("${idiomaMae}", "PT");
        MessageVO vo = new MessageVO("user", prompt);
        listaMensagens.add(vo);
        chatRequest.setMessages(listaMensagens);
        chatRequest.setModel("gpt-3.5-turbo");
        return openAIservice.callOpenAI(chatRequest);
    }
}

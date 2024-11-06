package br.com.api.youspeaking.feature.Translation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;

import br.com.api.youspeaking.feature.Thirdparties.OpenAI.OpenApiService;
import br.com.api.youspeaking.utils.Utils;
import br.com.api.youspeaking.vo.ChatRequestVO;
import br.com.api.youspeaking.vo.MessageVO;
// import jakarta.transaction.Transactional;

// @Transactional
@Service
public class TranslationService {

    @Autowired OpenApiService openAIservice;

    public ObjectNode   translateText(String text, String target) throws JsonProcessingException {
        ObjectNode response     = Utils.genericJsonSuccess();
        response.put("data", langDetecString(text, target) );
        return response;
    }

    protected String langDetecString(String text, String target) throws JsonProcessingException{
        ChatRequestVO chatRequest = new ChatRequestVO();
        List<MessageVO> listaMensagens = new ArrayList();

        String prompt = "Traduza o texto: ${texto} | para o idioma: ${target} | retorne somente o texto traduzido em string";
        prompt = prompt.replace("${texto}", text);
        prompt = prompt.replace("${target}", target);

        MessageVO vo = new MessageVO(prompt, "user");
        listaMensagens.add(vo);
        chatRequest.setMessages(listaMensagens);
        chatRequest.setModel("gpt-4o-mini");
        ObjectNode responseChat   = openAIservice.callOpenAI(chatRequest);

        String responseChatString = responseChat.get("choices").get(0).get("message").get("content").asText();
        try{
            return responseChatString.replaceAll("```|´´´", "").replace("json", "");
        } catch (Exception e) {
            return null;
        }
    }
}

package platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.remoting.soap.SoapFaultException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import platform.dto.CodeDto;
import platform.model.Code;
import platform.service.CodeService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class ViewController {

    @Autowired
    private CodeService codeService;

    public ViewController(CodeService codeService) {
        this.codeService = codeService;
    }

    @GetMapping(value = "/code/{id}", produces = MediaType.TEXT_HTML_VALUE)
    public String getHtmlCode(Model model, @PathVariable UUID id) throws HttpStatusCodeException {
        String ret = "";
        codeService.updateCodeById(id);
        System.out.println("CONDITIONS =>" + String.valueOf( codeService.getCodeById(id) ) + "RbT=" + codeService.getCodeById(id).map(Code::isRestrictedByTime).orElse(null) + "RbV=" + codeService.getCodeById(id).map(Code::isRestrictedByViews).orElse(null) ) ;
        if (codeService.getCodeById(id).isEmpty()) {
            ret = null;
        } else {
            model.addAttribute("code", new CodeDto( codeService.getCodeById(id).get()) );
            if (codeService.getCodeById(id).get().isRestricted()) {
                if ( codeService.getCodeById(id).get().isRestrictedByTime() && (!codeService.getCodeById(id).get().isRestrictedByViews()) ) {
                    ret = "CodeTimeRestricted";
                } else if ( codeService.getCodeById(id).get().isRestrictedByViews() && (!codeService.getCodeById(id).get().isRestrictedByTime()) ) {
                    ret = "CodeViewsRestricted";
                } else {
                    ret = "CodeRestricted";
                }
            } else {
                ret = "Code";
            }
        }
        return ret;
    }

    @GetMapping(value = "/code/latest", produces = MediaType.TEXT_HTML_VALUE)
    public String getHtmlCodeLatest(Model model) {
        List<CodeDto> listDto = new ArrayList<>();
        for (Code code : codeService.getCodeList()) {
            if (!code.isRestricted()) {
                listDto.add(new CodeDto( code ) );
            }
        }
        listDto = listDto.stream().skip(Math.max(0, listDto.size()-10)).collect(Collectors.toList());
        Collections.reverse(listDto);
        model.addAttribute("latestCodeList", listDto );
        return "LatestCodes";
    }

    @GetMapping(value = "/code/new", produces = MediaType.TEXT_HTML_VALUE)
    public String createHtmlCode() {
        return "CreateCode";
    }

}
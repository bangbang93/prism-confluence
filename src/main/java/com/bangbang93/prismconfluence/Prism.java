package com.bangbang93.prismconfluence;

import com.atlassian.confluence.content.render.xhtml.ConversionContext;
import com.atlassian.confluence.macro.Macro;
import com.atlassian.confluence.macro.MacroExecutionException;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.webresource.api.assembler.PageBuilderService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@Scanned
public class Prism implements Macro {

    private PageBuilderService pageBuilderService;

    @Autowired
    public Prism(@ComponentImport PageBuilderService pageBuilderService) {
        this.pageBuilderService = pageBuilderService;
    }

    @Override
    public String execute(Map<String, String> map, String s, ConversionContext conversionContext) throws MacroExecutionException {
        pageBuilderService.assembler().resources().requireWebResource("com.bangbang93.PrismConfluence:PrismConfluence-resources");
        String language = "typescript";
        if (map.get("language") != null) {
            language = map.get("language");
        }
        String lineNumbers = " line-numbers";
        if ("false".equals(map.get("line-numbers"))) {
            lineNumbers = "";
        }
        return String.format("<script type=\"text/plain\" class=\"language-%s%s\">%s</script>", language, lineNumbers, s);
    }

    @Override
    public BodyType getBodyType() {
        return BodyType.PLAIN_TEXT;
    }

    @Override
    public OutputType getOutputType() {
        return OutputType.BLOCK;
    }
}

package com.waracle.cakemgr;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.waracle.cakemgr.dto.CakeDTO;
import com.waracle.cakemgr.service.CakeService;

@Component
public class AppStartupRunner implements ApplicationRunner {

	@Autowired
	CakeService cakeService;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		InputStream inputStream = new URL("https://gist.githubusercontent.com/hart88/198f29ec5114a3ec3460/raw/8dd19a88f9b8d24c23d9960f3300d0c917a4f07c/cake.json").openStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        StringBuffer buffer = new StringBuffer();
        String line = reader.readLine();
        while (line != null) {
            buffer.append(line);
            line = reader.readLine();
        }

        System.out.println("parsing cake json");
        JsonParser parser = new JsonFactory().createParser(buffer.toString());
        if (JsonToken.START_ARRAY != parser.nextToken()) {
            throw new Exception("bad token");
        }

        JsonToken nextToken = parser.nextToken();
        while(nextToken == JsonToken.START_OBJECT) {
            System.out.println("creating cake entity");

            CakeDTO cakeEntity = new CakeDTO();
            System.out.println(parser.nextFieldName());
            cakeEntity.setTitle(parser.nextTextValue());

            System.out.println(parser.nextFieldName());
            cakeEntity.setDescription(parser.nextTextValue());

            System.out.println(parser.nextFieldName());
            cakeEntity.setImageUrl(parser.nextTextValue());

            cakeService.save(cakeEntity);
            
            nextToken = parser.nextToken();
            System.out.println(nextToken);

            nextToken = parser.nextToken();
            System.out.println(nextToken);
	}
	}

}

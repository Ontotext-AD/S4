/*
* Copyright (c) 2015
*
* This file is part of the s4.ontotext.com REST client library, and is
* licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.ontotext.s4.api.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * @author Tsvetan Dimitrov <tsvetan.dimitrov@ontotext.com>
 * <p/>
 * Date added: 2/23/15.
 */
public class FileUtils {

    public static String readFileToString(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    public static List<File> listRawTextFiles(String filePath) {
        File sourceDir = new File(filePath);
        if (sourceDir.exists()) {
            final File[] fileArray = sourceDir.listFiles();
            if (fileArray != null) {
                return Arrays.asList(fileArray);
            }
        }
        return null;
    }

}

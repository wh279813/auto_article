package com.hackathon.autoarticle.nlp;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author minlingchao
 * @version V1.0
 * @description:
 * @date 2020/09/26 10:45 上午
 */
@Data
public class DependencyParserResp implements Serializable {

    private List<Word> words;

    @Data
    public static class Word implements Serializable{
        private Long id;

        private String word;

        private String pos;

        @SerializedName("head_word_id")
        private Long headWordId;

        @SerializedName("dependency_label")
        private String dependencyLabel;

        @Override
        public String toString() {
            return "Word{" +
                    "id=" + id +
                    ", word='" + word + '\'' +
                    ", pos='" + pos + '\'' +
                    ", headWordId=" + headWordId +
                    ", dependencyLabel='" + dependencyLabel + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "DependencyParserResp{" +
                "words=" + words +
                '}';
    }
}

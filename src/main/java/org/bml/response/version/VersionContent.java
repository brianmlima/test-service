package org.bml.response.version;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import lombok.experimental.Accessors;

import java.util.LinkedList;
import java.util.List;


/**
 * An encapsulation of the Version endpoints response content.
 */
@JsonDeserialize(builder = VersionContent.VersionContentBuilder.class)
@Builder(builderClassName = "VersionContentBuilder", toBuilder = true)
@Getter
@Accessors(fluent = true)
public class VersionContent {

    @Singular
    @JsonProperty(required = false)
    private List<String> errors = new LinkedList<>();

    @JsonProperty(required = false)
    private String version;

    @JsonProperty(required = false)
    private Build build;

    @JsonProperty(required = false)
    private Scm scm;

    @JsonPOJOBuilder(withPrefix = "")
    public static class VersionContentBuilder {
    }

    @JsonDeserialize(builder = Build.BuildBuilder.class)
    @Builder(builderClassName = "BuildBuilder", toBuilder = true)
    @Getter
    @Accessors(fluent = true)
    public static class Build {
        @JsonProperty
        private String profile;

        @JsonPOJOBuilder(withPrefix = "")
        public static class BuildBuilder {
        }
    }

    @JsonDeserialize(builder = Scm.ScmBuilder.class)
    @Builder(builderClassName = "ScmBuilder", toBuilder = true)
    @Getter
    @Accessors(fluent = true)
    public static class Scm {
        @JsonProperty
        private String branch;
        @JsonProperty
        private Commit commit;

        @JsonPOJOBuilder(withPrefix = "")
        public static class ScmBuilder {
        }
    }

    @JsonDeserialize(builder = Commit.CommitBuilder.class)
    @Builder(builderClassName = "CommitBuilder", toBuilder = true)
    @Getter
    @Accessors(fluent = true)
    public static class Commit {
        @JsonProperty
        private String message;
        @JsonProperty
        private Hash hash;

        @JsonPOJOBuilder(withPrefix = "")
        public static class CommitBuilder {
        }
    }

    @JsonDeserialize(builder = Hash.HashBuilder.class)
    @Builder(builderClassName = "HashBuilder", toBuilder = true)
    @Getter
    @Accessors(fluent = true)
    public static class Hash {
        @JsonProperty
        private String type;
        @JsonProperty
        private String full;
        @JsonProperty
        private String eight;

        @JsonPOJOBuilder(withPrefix = "")
        public static class HashBuilder {
        }
    }

}





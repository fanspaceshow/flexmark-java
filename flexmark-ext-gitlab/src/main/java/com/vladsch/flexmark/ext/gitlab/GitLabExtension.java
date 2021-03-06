package com.vladsch.flexmark.ext.gitlab;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.ext.gitlab.internal.*;
import com.vladsch.flexmark.formatter.internal.Formatter;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.DataKey;
import com.vladsch.flexmark.util.options.MutableDataHolder;

/**
 * Extension for git_labs
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * ({@link com.vladsch.flexmark.parser.Parser.Builder#extensions(Iterable)},
 * {@link com.vladsch.flexmark.html.HtmlRenderer.Builder#extensions(Iterable)}).
 * </p>
 * <p>
 * The parsed GitLab Flavoured Markdown
 * </p>
 */
public class GitLabExtension implements Parser.ParserExtension
        , HtmlRenderer.HtmlRendererExtension
        , Formatter.FormatterExtension
        // , Parser.ReferenceHoldingExtension
{
    // public static final DataKey<KeepType> GIT_LABS_KEEP = new DataKey<KeepType>("GIT_LABS_KEEP", KeepType.FIRST); // standard option to allow control over how to handle duplicates
    // public static final DataKey<GitLabRepository> GIT_LABS = new DataKey<GitLabRepository>("GIT_LABS", new DataValueFactory<GitLabRepository>() { @Override public GitLabRepository create(DataHolder options) { return new GitLabRepository(options); } });
    public static final DataKey<Boolean> INS_PARSER = new DataKey<Boolean>("INS_PARSER", true);
    public static final DataKey<Boolean> DEL_PARSER = new DataKey<Boolean>("DEL_PARSER", true);
    public static final DataKey<Boolean> BLOCK_QUOTE_PARSER = new DataKey<Boolean>("BLOCK_QUOTE_PARSER", true);
    public static final DataKey<Boolean> NESTED_BLOCK_QUOTES = new DataKey<Boolean>("NESTED_BLOCK_QUOTES", true);
    public static final DataKey<Boolean> INLINE_MATH_PARSER = new DataKey<Boolean>("INLINE_MATH_PARSER", true);
    public static final DataKey<Boolean> RENDER_BLOCK_MATH = new DataKey<Boolean>("RENDER_BLOCK_MATH", true);
    public static final DataKey<Boolean> RENDER_BLOCK_MERMAID = new DataKey<Boolean>("RENDER_BLOCK_MERMAID", true);
    public static final DataKey<Boolean> RENDER_VIDEO_IMAGES = new DataKey<Boolean>("RENDER_VIDEO_IMAGES", true);
    public static final DataKey<Boolean> RENDER_VIDEO_LINK = new DataKey<Boolean>("RENDER_VIDEO_LINK", true);

    public static final DataKey<String> INLINE_MATH_CLASS = new DataKey<String>("INLINE_MATH_CLASS", "katex");
    public static final DataKey<String> BLOCK_MATH_CLASS = new DataKey<String>("BLOCK_MATH_CLASS", "katex");
    public static final DataKey<String> BLOCK_MERMAID_CLASS = new DataKey<String>("BLOCK_MERMAID_CLASS", "mermaid");
    public static final DataKey<String> VIDEO_IMAGE_CLASS = new DataKey<String>("VIDEO_IMAGE_CLASS", "video-container");
    public static final DataKey<String> VIDEO_IMAGE_LINK_TEXT_FORMAT = new DataKey<String>("VIDEO_IMAGE_LINK_TEXT_FORMAT", "Download '%s'");
    public static final DataKey<String> BLOCK_INFO_DELIMITERS = new DataKey<String>("BLOCK_INFO_DELIMITERS", " ");
    public static final DataKey<String> VIDEO_IMAGE_EXTENSIONS = new DataKey<String>("VIDEO_IMAGE_EXTENSIONS", "mp4,m4v,mov,webm,ogv");

    private GitLabExtension() {
    }

    public static Extension create() {
        return new GitLabExtension();
    }

    @Override
    public void rendererOptions(final MutableDataHolder options) {

    }

    @Override
    public void parserOptions(final MutableDataHolder options) {

    }

    @Override
    public void extend(final Formatter.Builder builder) {
        builder.nodeFormatterFactory(new GitLabNodeFormatter.Factory());
    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        GitLabOptions options = new GitLabOptions(parserBuilder);
        if (options.blockQuoteParser) {
            parserBuilder.customBlockParserFactory(new GitLabBlockQuoteParser.Factory());
        }

        if (options.delParser || options.insParser) {
            parserBuilder.customInlineParserExtensionFactory(new GitLabInlineParser.Factory());
        }

        if (options.inlineMathParser) {
            parserBuilder.customInlineParserExtensionFactory(new GitLabInlineMathParser.Factory());
        }
    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder, String rendererType) {
        if (rendererBuilder.isRendererType("HTML")) {
            rendererBuilder.nodeRendererFactory(new GitLabNodeRenderer.Factory());
        } else if (rendererBuilder.isRendererType("JIRA")) {
            //rendererBuilder.nodeRendererFactory(new GitLabJiraRenderer.Factory());
        }
    }
}

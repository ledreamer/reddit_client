package satyaki.com.reddit_client;

/**
 * Created by satyaki on 19/10/15.
 */
/**
 * This class holds the details about a comment
 * @author Satyaki
 */
public class Comment {
    String htmlText;
    String author;
    String points;
    String postedOn;

    // The 'level' field indicates how deep in the hierarchy
    // this comment is. A top-level comment has a level of 0
    // where as a reply has level 1, and reply of a reply has
    // level 2 and so on...
    int level;
}
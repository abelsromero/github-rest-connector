package asr.test.rest

import groovy.json.JsonSlurper

/**
 * Created by abelsr on 19/08/2015.
 */
class GitHubConnector {

    private static final String API_PATH = "https://api.github.com/repos"

    // authentication is requires for some queries once a limit is reached
    String username
    String password

    String repoOwner
    String repoName


    public GitHubConnector (String repoOwner, String repoName) {
        this.repoOwner = repoOwner
        this.repoName = repoName
    }

    def getMilestones () {
        URL url = new URL("$API_PATH/$repoOwner/$repoName/milestones")
        return execGETRequest(url)
    }

    /**
     *
     * @param milestone milestone's number
     */
    def getIssues (Integer milestone = null, String state = "all", String... labels) {

        String url = "$API_PATH/$repoOwner/$repoName/issues?state=$state"
        if (milestone)
            url = "$url&milestone=$milestone"
        if (labels)
            url = "$url$labels=${labels.join(',')}"

        return execGETRequest(url)
    }

    /**
     *
     * @param milestone milestone's number
     */
    def getLabels () {
        String url = "$API_PATH/$repoOwner/$repoName/labels"
        return execGETRequest(url)
    }

    /**
     * If username is set, returns the same connection after applying Basic Authentication
     */
    private URLConnection doAuthentication(URLConnection connection) {
        if (username) {
            String encoded = "$username:$password".bytes.encodeBase64().toString()
            connection.setRequestProperty("Authorization", "Basic $encoded");
        }
        return connection
    }

    /**
     * Executes a GET request an returns the result in a JsonSlurper
     */
    private JsonSlurper execGETRequest(String url){
        URLConnection connection = doAuthentication(new URL(url).openConnection())
        InputStreamReader reader = new InputStreamReader(connection.inputStream)
        return new JsonSlurper().parse(reader)
    }
}

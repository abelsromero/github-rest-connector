import asr.test.rest.GitHubConnector

/**
 * Created by abelsr on 19/08/2015.
 */


GitHubConnector c = new GitHubConnector('asciidoctor', 'asciidoctorj')
GitHubConnector c2 = new GitHubConnector('asciidoctor', 'asciidoctor')
c.username = 'abelsromero'
c.password = 's45rGTodos'

c.getMilestones().each { m ->

    println "* Milestone: $m.title"

    c.getIssues(m.number).each { i ->
        println """#$i.number $i.title by @${i.user.login}
\t\t${i.state.capitalize()}\tLabels: ${i.labels*.name.join(',')}"""
    }
    println "\n"
}


c2.getLabels().each { l ->
    println "|${l.name}\n|\n|\n"
}

//create CHANGELOG.adoc
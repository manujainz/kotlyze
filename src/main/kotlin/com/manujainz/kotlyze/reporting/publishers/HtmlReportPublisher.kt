package com.manujainz.kotlyze.reporting.publishers

import com.manujainz.kotlyze.reporting.model.IssueType
import com.manujainz.kotlyze.reporting.model.PolicyViolation
import java.io.File
import java.util.*

class HtmlReportPublisher(outputPath: String? = null) : ReportPublisher {

    private val reportPath = outputPath ?: "app/build/reports/kotlyze"

    override fun publish(violations: EnumMap<IssueType, MutableList<PolicyViolation>>) {
        val stringBuilder = StringBuilder()
        stringBuilder.append(createHtmlHeader())

        violations.forEach { (issueType, policyViolations) ->
            stringBuilder.append(createTableForIssueType(issueType, policyViolations))
        }

        stringBuilder.append(createHtmlFooter())

        // Ensure the directory exists
        val outputFile = File(reportPath, "kotlyze-report.html").apply {
            parentFile.mkdirs()
        }

        println("HTML Report will be written to: $reportPath")

        outputFile.writeText(stringBuilder.toString())
    }

    private fun createHtmlHeader(): String = """
        <!DOCTYPE html>
        <html lang="en">
        <head>
            <meta charset="UTF-8">
            <meta http-equiv="X-UA-Compatible" content="IE=edge">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Kotlyze Report</title>
            <style>
                body { font-family: Arial, sans-serif; }
                table { width: 100%; border-collapse: collapse; margin-bottom: 20px; }
                th, td { padding: 8px; text-align: left; border-bottom: 1px solid #ddd; }
                th { background-color: #f2f2f2; }
            </style>
        </head>
        <body>
        <h1>Kotlyze Analysis Report</h1>
    """.trimIndent()

    private fun createTableForIssueType(issueType: IssueType, violations: List<PolicyViolation>): String {
        val tableHeaders = "<tr><th>File</th><th>Line</th><th>Message</th><th>Policy</th></tr>"
        val tableRows = violations.joinToString("") { violation ->
            "<tr>" +
                    "<td>${violation.issue.fileName}</td>" +
                    "<td>${violation.issue.lineNo}</td>" +
                    "<td>${violation.issue.msg}</td>" +
                    "<td>${violation.policyId}</td>" +
                    "</tr>"
        }

        return """
            <h2>${issueType.name}</h2>
            <table>
                $tableHeaders
                $tableRows
            </table>
        """.trimIndent()
    }

    private fun createHtmlFooter(): String = """
        </body>
        </html>
    """.trimIndent()
}

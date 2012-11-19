<%@ page import="socialissues.IssuesForm" %>



<div class="fieldcontain ${hasErrors(bean: issuesFormInstance, field: 'title', 'error')} ">
	<label for="title">
		<g:message code="issuesForm.title.label" default="Title" />
		
	</label>
	<g:textField name="title" value="${issuesFormInstance?.title}"/>
</div>


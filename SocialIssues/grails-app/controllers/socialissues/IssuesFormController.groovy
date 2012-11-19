package socialissues

import org.springframework.dao.DataIntegrityViolationException

class IssuesFormController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [issuesFormInstanceList: IssuesForm.list(params), issuesFormInstanceTotal: IssuesForm.count()]
    }

    def create() {
        [issuesFormInstance: new IssuesForm(params)]
    }

    def save() {
        def issuesFormInstance = new IssuesForm(params)
        if (!issuesFormInstance.save(flush: true)) {
            render(view: "create", model: [issuesFormInstance: issuesFormInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'issuesForm.label', default: 'IssuesForm'), issuesFormInstance.id])
        redirect(action: "show", id: issuesFormInstance.id)
    }

    def show(Long id) {
        def issuesFormInstance = IssuesForm.get(id)
        if (!issuesFormInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'issuesForm.label', default: 'IssuesForm'), id])
            redirect(action: "list")
            return
        }

        [issuesFormInstance: issuesFormInstance]
    }

    def edit(Long id) {
        def issuesFormInstance = IssuesForm.get(id)
        if (!issuesFormInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'issuesForm.label', default: 'IssuesForm'), id])
            redirect(action: "list")
            return
        }

        [issuesFormInstance: issuesFormInstance]
    }

    def update(Long id, Long version) {
        def issuesFormInstance = IssuesForm.get(id)
        if (!issuesFormInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'issuesForm.label', default: 'IssuesForm'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (issuesFormInstance.version > version) {
                issuesFormInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'issuesForm.label', default: 'IssuesForm')] as Object[],
                          "Another user has updated this IssuesForm while you were editing")
                render(view: "edit", model: [issuesFormInstance: issuesFormInstance])
                return
            }
        }

        issuesFormInstance.properties = params

        if (!issuesFormInstance.save(flush: true)) {
            render(view: "edit", model: [issuesFormInstance: issuesFormInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'issuesForm.label', default: 'IssuesForm'), issuesFormInstance.id])
        redirect(action: "show", id: issuesFormInstance.id)
    }

    def delete(Long id) {
        def issuesFormInstance = IssuesForm.get(id)
        if (!issuesFormInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'issuesForm.label', default: 'IssuesForm'), id])
            redirect(action: "list")
            return
        }

        try {
            issuesFormInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'issuesForm.label', default: 'IssuesForm'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'issuesForm.label', default: 'IssuesForm'), id])
            redirect(action: "show", id: id)
        }
    }
}

package socialissues



import org.junit.*
import grails.test.mixin.*

@TestFor(IssuesFormController)
@Mock(IssuesForm)
class IssuesFormControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/issuesForm/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.issuesFormInstanceList.size() == 0
        assert model.issuesFormInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.issuesFormInstance != null
    }

    void testSave() {
        controller.save()

        assert model.issuesFormInstance != null
        assert view == '/issuesForm/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/issuesForm/show/1'
        assert controller.flash.message != null
        assert IssuesForm.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/issuesForm/list'

        populateValidParams(params)
        def issuesForm = new IssuesForm(params)

        assert issuesForm.save() != null

        params.id = issuesForm.id

        def model = controller.show()

        assert model.issuesFormInstance == issuesForm
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/issuesForm/list'

        populateValidParams(params)
        def issuesForm = new IssuesForm(params)

        assert issuesForm.save() != null

        params.id = issuesForm.id

        def model = controller.edit()

        assert model.issuesFormInstance == issuesForm
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/issuesForm/list'

        response.reset()

        populateValidParams(params)
        def issuesForm = new IssuesForm(params)

        assert issuesForm.save() != null

        // test invalid parameters in update
        params.id = issuesForm.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/issuesForm/edit"
        assert model.issuesFormInstance != null

        issuesForm.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/issuesForm/show/$issuesForm.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        issuesForm.clearErrors()

        populateValidParams(params)
        params.id = issuesForm.id
        params.version = -1
        controller.update()

        assert view == "/issuesForm/edit"
        assert model.issuesFormInstance != null
        assert model.issuesFormInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/issuesForm/list'

        response.reset()

        populateValidParams(params)
        def issuesForm = new IssuesForm(params)

        assert issuesForm.save() != null
        assert IssuesForm.count() == 1

        params.id = issuesForm.id

        controller.delete()

        assert IssuesForm.count() == 0
        assert IssuesForm.get(issuesForm.id) == null
        assert response.redirectedUrl == '/issuesForm/list'
    }
}

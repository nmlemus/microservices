package devices.ms

class BootStrap {

    def init = { servletContext ->
		Role admin = new Role("ROLE_ADMIN").save()
		User user = new User("user", "pass").save()
		UserRole.create(user, admin, true)
    }
	
    def destroy = {
    }
}

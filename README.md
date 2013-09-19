Provides a full system to help you develop Android applications that have a free version and some commercial unlocking application.

## Getting started

### Create the required projects

1. Import the "library" folder in your workspace as a library project. This is where authorization gets verified.
1. Create a library project for your application, that will be shared between your free application and the unlocker application.
1. Create an application project for the unlocker. That project should depend on both of the library projects you created in steps 1 and 2.
1. Create an application project for the free app.  That project should depend on both of the library projects you created in steps 1 and 2.

### Implement
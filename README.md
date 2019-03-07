# Medium Connector - Work in Progress

This connector retrieves medium posts from your medium account and stores them in nodes within the JCR. 
Additionally these posts are processed in Experience Fragments.

## TODO

- Put styling on different Medium XF variations
    - Make clientlibs
    - Inject clientlibs in experience fragments @ `aem-medium-connector/apps/src/main/content/jcr_root/conf/medium/settings/wcm/policies`
- Make service user configurable
- Look at issue where workflows dont terminate after completing workflowsteps
- Add unit tests !

## Prerequisites

### Service user

1. Login on `http://localhost:4502/crx/explorer/index.jsp`
2. Navigate to User Administration > Create System User
3. Create `medium-service-user` 
4. Assign permissions to `/apps/cq/experience-fragments` in `http://localhost:4502/useradmin`
    - /conf/medium
    - /apps/cq/experience-fragments
    - /apps/medium-connector

## How to build

To build all the modules run in the project root directory the following command with Maven 3:

    mvn clean install

If you have a running AEM instance you can build and package the whole project and deploy into AEM with  

    mvn clean install -PautoInstallPackage
    
Or to deploy it to a publish instance, run

    mvn clean install -PautoInstallPackagePublish
    
Or alternatively

    mvn clean install -PautoInstallPackage -Daem.port=4503

Or to deploy only the bundle to the author, run

    mvn clean install -PautoInstallBundle

## Testing

There are three levels of testing contained in the project:

* unit test in core: this show-cases classic unit testing of the code contained in the bundle. To test, execute:

    mvn clean test

## Maven settings

The project comes with the auto-public repository configured. To setup the repository in your Maven settings, refer to:

    http://helpx.adobe.com/experience-manager/kb/SetUpTheAdobeMavenRepository.html

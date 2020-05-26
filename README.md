# new_movies
Udacity devops capstone

### Application
- The application is a Springboot MVC app for adding movie reviews

### Docker Container 
- Container repository is https://hub.docker.com/repository/docker/crististoica/new-movie

### Infrastructure Files

/infrastructure folder contains all yaml files

- I used CloudFormation to deploy the network
- With the outputs from the network stack I deployed the EKS cluster with eksctl (using create_cluster.sh commands)
- I deployed my initial deployment / service to the cluster using initial_deploy.sh script

### Jenkins pipeline , blue green deployment

Steps:
1. Checkout code
2. Lint (gradlew checkstyle) java code, using config/checkstyle.xml configs
3. Build jar bundle
4. Build docker container which runs the jar bundle at startup
5. Push the image to docker hub (link above)
6. Configure kubeconfig with jenkins aws credentials
7. get BLUE (currently deployed) version
8. Deploy the current (GREEN) build , replacing the deployed deployment yaml with the current build version
9. Check GREEN deploy rollout is successful
10. Deploy new service (load balancer) in the same fashion as step 8 - get existing yaml and replace version with current build version
11. Delete BLUE deployment



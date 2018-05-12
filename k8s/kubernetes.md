Kubernetes
-

`kubectl get nodes`

`kubectl get pods`

Deployment
-
Create deployment 

`kubectl create -f deployment-azure.yml`

Get the deployments

`kubectl get deployment`

View deployment

`kubectl describe deployment keepcalm-food`

Check if service AlreadyExists

`kubectl get service keepcalm-food`

otherwise create one.

`kubectl expose deployment keepcalm-food --type=LoadBalancer --port=80 --target-port=8080`

Check _pods_ 

`kubectl get pods -l app=keepcalm-food`




Update the version of the app https://kubernetes.io/docs/tutorials/kubernetes-basics/update-interactive/
-
To list your deployments use the get deployments command: 

`kubectl get deployments`

To list the running Pods use the get pods command:

`kubectl get pods`

To view the current image version of the app, run a describe command against the Pods (look at the Image field):

`kubectl describe pods`

To update the image of the application to version 2, use the set image command, followed by the deployment name and the new image version:

`kubectl set image deployments/keepcalm-food keepcalm-food=keepcalmregistry.azurecr.io/keepcalm-food:v2`

Check the status of the new Pods

`kubectl get pods`

Check if App is runing

`kubectl describe services/keepcalm-food`

kubectl rollout status 

`kubectl rollout status deployments/keepcalm-food`

Check current image

`kubectl describe pods`

Update deployment

`kubectl apply -f deployment-mlab-v2.yml`


Redepoy
`kubectl replace --force -f  mlab-pod.yaml`
 







Secret mLab
-
create base63 encoded username
`echo mongoDbUser | base64`

create base64 encoded password
`echo EWjELjkj%HT]DmzkREwMEpMsGK% | base64`

Copy the vaule in mlab-secret.yml
create a generic secret from YML file
`kubectl create -f mlab-secret.yml`
 
View inforamtion about the Secret
 
`kubectl get secret mlab-secret`

Details about the Secret

'kubectl describe secret mlab-secret'
 
 
Create Secret POD
-
`kubectl create -f mlab-secret-pod.yml`

Verify your POD

`kubectl get pod mlab-secret-pod`

Access the Secret in the POD
`kubectl exec -it mlab-secret-env-pod /bin/sh`

`env`






Clean Up Secret mLab
-
`kubectl delete -f mlab-secret.yml -f mlab-secret-pod.yml`










Docker push image
-
`mvn package docker:build -DpushImage`

If: Exception caught: unauthorized: authentication required
make it sep by step


Docker login and Push
-
`docker login -u keepcalmregistry -p "N/0o1mNRdg=htJA/4MhwkDtIRX7KGH6m" keepcalmregistry.azurecr.io`

`docker push keepcalmregistry.azurecr.io/keepcalm-food`

Tag image lastes with v2
-
`mvn package docker:build -DpushImageTags -DdockerImageTags=latest -DdockerImageTags=v2`

Push to docker

`docker push keepcalmregistry.azurecr.io/keepcalm-food:v2`


Deploy
-
`kubectl run keepcalm-food --image=keepcalmregistry.azurecr.io/keepcalm-food:latest`

Get Deplyments
-
`kubectl get deployments`

View Kubernetes 
-
`kubectl proxy`


Delete Deployments
-
`kubectl delete deployments keepcalm-food`








Create a Pod based on the YAML configuration file
-
`kubectl create -f envars.yaml`


List the running Pods
-
`kubectl get pods -l purpose=keepcalm-food-envars`

Show Logs
-
`kubectl logs keepcalm-food`
















https://docs.microsoft.com/en-us/java/azure/spring-framework/deploy-spring-boot-java-app-on-kubernetes



`az login`

- Create a resource group  
`az group create --name=keepcalm-kubernetes --location=eastus`

- Create a private Azure container registry in the resource group
`az acr create --admin-enabled --resource-group keepcalm-kubernetes --location eastus --name keepcalmregistry --sku Basic`

- Create a Kubernetes Cluster on AKS using the Azure CLI
`az acs create --orchestrator-type=kubernetes --resource-group=keepcalm-kubernetes --name=keepcalm-containerservice --dns-prefix=keepcalm-kubernetes`


- Install kubectl using the Azure CLI
`az acs kubernetes install-cli`

- Download the cluster configuration information 
`az acs kubernetes get-credentials --resource-group=keepcalm-kubernetes --name=keepcalm-containerservice`

- Deploy the image to your Kubernetes cluster
`kubectl run keepcalm-food --image=keepcalmregistry.azurecr.io/keepcalm-food:latest`

- Updating container image
`kubectl rolling-update --image=keepcalmregistry.azurecr.io/keepcalm-food:latest`


`az acs kubernetes browse --resource-group=keepcalm-kubernetes --name=keepcalm-containerservice`

`kubectl expose deployment keepcalm-food --type=LoadBalancer --port=80 --target-port=8080`















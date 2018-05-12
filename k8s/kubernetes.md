Kubernetes
-

`kubectl get nodes`

`kubectl get pods`

`kubectl get deployments`

`kubectl get service`


Docker
-
`mvn package docker:build -DpushImage`

If: Exception caught: unauthorized: authentication required
make it sep by step

- login

    `docker login -u <username> -p <password> keepcalmregistry.azurecr.io`

- push

    `docker push keepcalmregistry.azurecr.io/keepcalm-food:latest`




Secret keepcalm-food
-
- create base63 encoded username

    `echo <username> | base64`

- create base64 encoded password

    `echo <password> | base64`

- Copy the vaule in keepcalm-food-secret.yaml create a generic secret from YML file
    `kubectl create -f keepcalm-food-secret.yaml`
 
- View inforamtion about the Secret
 
    `kubectl get secret keepcalm-food-secret`

    - Details about the Secret

        `kubectl describe secret keepcalm-food-secret`


Secret POD
-
`kubectl create -f keepcalm-food-secret-pod.yaml`

- Verify your POD

    `kubectl get pod keepcalm-food-secret-pod`

- Access the Secret in the POD

    `kubectl exec -it keepcalm-food-secret-pod /bin/sh`
    
    Show environment

    `env`

-  Clean Up Secrets

    `kubectl delete -f keepcalm-food-secret.yaml -f keepcalm-food-secret-pod.yaml`


keepcalm-food POD
-
`kubectl create -f keepcalm-food-pod.yaml`

Deployment 
-

`kubectl create -f keepcalm-food-deployment.yaml`

- View deployment

    `kubectl describe deployment keepcalm-food`

- Expose deployment

    `kubectl expose deployment keepcalm-food --type=LoadBalancer --port=80 --target-port=8080`


- Update deployment
    how to make Deployment to update image
    
    `kubectl edit deployment keepcalm-food`

    

- Redepoy
    `kubectl apply -f keepcalm-food-deployment.yaml`

    `kubectl replace --force -f keepcalm-food-deployment.yaml`
    
    when not changing the container image name or tag you would just scale your application to 0 and back to the original size with sth like:

    `kubectl scale --replicas=0 deployment keepcalm-food`

    `kubectl scale --replicas=1 deployment keepcalm-food`
 
- Get the image ID of image by tag on Kubernetes deployment

    `kubectl get pod --namespace=default keepcalm-food-pod -o json | jq '.status.containerStatuses[] | { "image": .image, "imageID": .imageID }'`


 







 



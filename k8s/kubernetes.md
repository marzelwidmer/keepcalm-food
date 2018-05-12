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

- Copy the vaule in keepcalm-food-secret.yml create a generic secret from YML file
    `kubectl create -f keepcalm-food-secret.yml`
 
- View inforamtion about the Secret
 
    `kubectl get secret keepcalm-food-secret`

    - Details about the Secret

        `kubectl describe secret keepcalm-food-secret`


Secret POD
-
`kubectl create -f keepcalm-food-secret-pod.yml`

- Verify your POD

    `kubectl get pod keepcalm-food-secret-pod`

- Access the Secret in the POD

    `kubectl exec -it keepcalm-food-secret-pod /bin/sh`
    
    Show environment

    `env`

-  Clean Up Secrets

    `kubectl delete -f keepcalm-food-secret.yml -f keepcalm-food-secret-pod.yml`


keepcalm-food POD
-
`kubectl create -f keepcalm-food-pod.yml`

Deployment 
-

`kubectl create -f keepcalm-food-deployment.yml`

- View deployment

    `kubectl describe deployment keepcalm-food`

- Expose deployment

    `kubectl expose deployment keepcalm-food --type=LoadBalancer --port=80 --target-port=8080`


- Update deployment

    `kubectl apply -f keepcalm-food-deployment.yml`

- Redepoy

    `kubectl replace --force -f keepcalm-food-deployment.yml`
 













 



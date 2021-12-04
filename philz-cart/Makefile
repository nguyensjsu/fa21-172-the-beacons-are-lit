all: clean

clean:
	gradle clean

compile:
	gradle build

test:
	gradle test

jar: 
	gradle build -x test
	gradle bootJar

run: jar
	echo Starting Spring at:  http://localhost:8080
	java -jar build/libs/philz-order-1.0.jar

# Docker

docker-build: jar
	docker build -t philz-cart .
	docker images

docker-run: docker-build
	docker run --name philz-cart -td -p 80:8080 philz-cart
	docker ps

docker-clean:
	docker stop philz-cart
	docker rm philz-cart
	docker rmi philz-cart

docker-shell:
	docker exec -it philz-cart bash

docker-push:
	docker login
	docker build -t marymarkart/philz-cart:v2 -t marymarkart/philz-cart:v2 .
	docker push marymarkart/philz-cart:v2

# Compose

network-ls:
	docker network ls 

network-create:
	docker network create --driver bridge philz-cart-v2_network

network-prune:
	docker network prune

compose-up:
	docker-compose up --scale philz-cart=2 -d

compose-down:
	docker-compose down 	

lb-stats:
	echo "user = admin | password = admin"
	open http://localhost:1936

lb-test:
	open http://localhost


# Pod

pod-run:
	kubectl apply -f pod.yaml

pod-list:
	kubectl get pods

pod-desc:
	kubectl describe pods philz-cart

pod-delete:
	kubectl delete -f pod.yaml

pod-shell:
	kubectl exec -it philz-cart -- /bin/bash

pod-logs:
	kubectl logs -f philz-cart

# Deployment

deployment-create:
	kubectl create -f deployment.yaml --save-config 

deployment-get:
	kubectl get deployments

deployment-get-pods:
	kubectl get pods -l name=philz-cart

deployment-pod-shell:
	kubectl exec -it $(pod) -- /bin/bash

deployment-upgrade:
	kubectl apply  -f deployment.yaml

deployment-delete:
	kubectl delete deployment philz-cart-deployment

# Service

service-create:
	kubectl create -f service.yaml

service-get:
	kubectl get services

service-get-ip:
	kubectl get service philz-cart -o wide

service-delete:
	kubectl delete service philz-cart

# Ingress

ingress-apply:
	kubectl apply -f ingress.yaml

ingress-ip:
	kubectl get ingress philz-cart-ingress





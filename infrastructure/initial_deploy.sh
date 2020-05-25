#!/bin/bash

export APP_NAME=new-movies
export VERSION=v14
# update kubeconfig
aws eks update-kubeconfig --name my-cluster --kubeconfig /Users/geostoica/kubeconfig

# apply deployment
envsubst < k8s_config/deployment.yaml | kubectl --kubeconfig ~/kubeconfig apply -f -

# apply service
envsubst < k8s_config/service.yaml | kubectl --kubeconfig ~/kubeconfig apply -f -

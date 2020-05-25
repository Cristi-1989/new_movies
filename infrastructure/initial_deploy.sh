export APP_NAME=new-movies
export VERSION=v3

// apply deployment
envsubst < k8s_config/deployment.yaml | kubectl --kubeconfig ~/kubeconfig apply -f -

// apply service
envsubst < k8s_config/service.yaml | kubectl --kubeconfig ~/kubeconfig apply -f -

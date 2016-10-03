

job('bluenode1') {
    scm {
        git('git://github.com/quidryan/aws-sdk-test.git')
    }       
    steps {
        maven('-e clean test')
    }
}


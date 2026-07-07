(ns voxel-test
  (:require [clojure.test :refer [deftest is testing]]
            [voxel]))
(deftest namespace-loads
  (testing "the restored CLJC namespace loads"
    (is (some? (find-ns 'voxel)))))

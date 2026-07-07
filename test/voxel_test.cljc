(ns voxel-test
  (:require [clojure.test :refer [deftest is testing]]
            [voxel]))

(deftest namespace-loads
  (testing "the restored CLJC namespace loads"
    (is (some? (find-ns 'voxel)))))

;; Ported 1:1 from kami-voxel/src/lib.rs `mod tests`.

(deftest dense
  (let [v (voxel/dense-new 4 4 4)
        v (voxel/vol-set v 1 2 3 {:material 1 :color [1.0 1.0 1.0 1.0]})]
    (is (voxel/solid? (voxel/vol-get v 1 2 3)))
    (is (= 1 (voxel/vol-count-filled v)))))

(deftest sparse
  (let [v (voxel/sparse-new 8 8 8)
        v (voxel/vol-set v 3 3 3 {:material 2 :color [0.0 1.0 0.0 1.0]})]
    (is (voxel/solid? (voxel/vol-get v 3 3 3)))
    (is (= 1 (voxel/vol-count-filled v)))
    ;; Sparse-specific: filled_iter
    (is (= 1 (count (voxel/filled-iter v))))))

(deftest octree
  (let [v (voxel/octree-new 8)
        v (voxel/vol-set v 1 2 3 {:material 1 :color [1.0 1.0 1.0 1.0]})
        v (voxel/vol-set v 7 7 7 {:material 1 :color [0.0 0.0 1.0 1.0]})]
    (is (voxel/solid? (voxel/vol-get v 1 2 3)))
    (is (voxel/solid? (voxel/vol-get v 7 7 7)))
    (is (not (voxel/solid? (voxel/vol-get v 0 0 0))))))

(deftest wrapper-compat
  (let [vol (voxel/new-dense 4 4 4)
        vol (voxel/vol-set vol 0 0 0 {:material 1 :color [1.0 1.0 1.0 1.0]})
        sparse (voxel/to-sparse vol)]
    (is (= 1 (voxel/vol-count-filled sparse)))
    (is (= "sparse" (voxel/storage-type sparse)))))

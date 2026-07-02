# kotoba-lang/voxel

Zero-dep portable `.cljc` — restored from the legacy `kami-engine/kami-voxel` Rust crate
(kotoba-lang/kami-engine, deleted in PR #82 "Remove Rust workspace from kami-engine") as
part of the **clj-wgsl migration** (ADR-2607010930, `com-junkawasaki/root`).

## Status

Restored. `src/voxel.cljc` ports the original `kami-voxel/src/lib.rs` (410 lines) 1:1:
the `Voxel` value type, the `Volume` trait, and its three storage backends —
`DenseVolume` (flat W*H*D array), `SparseVolume` (map of only-filled cells),
`OctreeVolume` (adaptive power-of-2 tree) — plus the `VoxelVolume` wrapper
(`new-dense`/`new-sparse`/`new-octree`, `to-sparse`, `storage-type`). Every backend is a
plain CLJC map tagged with `:type`, with the trait methods becoming generic
`case`-dispatching functions (`vol-get`/`vol-set`/`vol-count-filled`/...). Pure data +
pure functions throughout — no IO/GPU (native execution stays substrate).

All 4 original Rust `#[test]`s (`dense`, `sparse`, `octree`, `wrapper_compat`) are
ported 1:1 to `test/voxel_test.cljc`, plus 1 namespace-loads smoke test — **5 tests /
11 assertions, 0 failures**.

## Develop

```bash
clojure -M:test
```

# HMI × Point Blank Patch

A small client-side compatibility patch for **Hold My Items** and **Vic's Point Blank** on Fabric 1.21.11.

---

## The Problem

Hold My Items overrides first-person rendering for *all* held items via a deep redirect into Minecraft's render pipeline. This means Point Blank's custom gun animations, ADS/scoping, and first-person visuals are completely broken when both mods are installed together.

## What This Patch Does

Intercepts HMI's render hook and bypasses it selectively when a Point Blank gun is being held. This restores VPB's native animations and scoping. All other items should continue to use HMI's animations as normal. Again, this is a small patch I made in an hour, so expect issues tbh.

## Requirements

| Dependency | Version  |
|---|----------|
| Minecraft | 1.21.11  |
| Fabric Loader | ≥ 0.18.6 |
| Hold My Items | ≥ 5.1    |
| Vic's Point Blank | ≥ 1.21.11-2.0 |

## Installation

Drop the jar into your `mods/` folder alongside Hold My Items and Point Blank. No configuration needed.

## Notes

- Client-side only — safe for servers, will not cause missing mod errors
- This is my first uploaded mod — feedback and issues welcome!
- If a future update to either mod breaks compatibility, open an issue

## Building from Source

```bash
./gradlew build
```

Output: `build/libs/HoldMyGuns-<version>.jar`

---

*Tested on Fabric 1.21.11 with Hold My Items 5.1.1 and Vic's Point Blank 2.0.*
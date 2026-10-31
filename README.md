# NTM Additions

A small NeoForge 1.21.1 addon for [NTM Continuum](https://github.com/Warfactory-Official/NTM-Continuum) and [Golem Core](https://github.com/Warfactory-Official/Golem-Core).

It adds Curios-slot versions of NTM's two armor-insert charms:

- **Meteor Charm (Curio)** — stops meteorite strikes and negates broadcaster damage.
- **Charm of Protection (Curio)** — diverts meteorite strikes, prevents their block damage, and halves broadcaster damage.

The original `hbm:meteor_charm` and `hbm:protection_charm` armor inserts are untouched. Recipes for the Curios versions are intentionally not included yet.

## Requirements

| Dependency | Version |
| --- | --- |
| Minecraft | 1.21.1 |
| NeoForge | 21.1.248+ |
| NTM Continuum (`hbm`) | 1.0.3+ |
| Golem Core (`golemcore`) | 1.0.3+ |
| Curios (`curios`) | 9.5.1+1.21.1 |

## How it works

- Both items implement Curios' `ICurioItem` and are added to both the `curios:charm` and `curios:curio` item tags, so they fit in a charm slot or any other Curios slot.
- The addon also ships a `charm` slot definition under its own namespace. Curios merges it with the built-in charm slot when one exists, or supplies the slot when it does not.
- Curios 9.5.1 does not assign any slots to players by default, so the addon ships `data/ntmadditions/curios/entities/player.json` to assign the standard slots (`head`, `hands`, `charm`, `ring`, `belt`, `back`, `necklace`, `bracelet`, `body`, and `curio`) to `minecraft:player`.
- A Golem Core `DamageEvents` listener reproduces the broadcaster-damage effect.
- A small Mixin into `com.hbm.handler.BossSpawnHandler#strike` makes the meteor checks see the Curios items too.

## Commands

`/ntmadditions meteor [targets]` (permission level 2) calls NTM's natural meteor strike path for the targeted players. With no target it strikes the executing player. It uses NTM's normal spawn height, random offset, fall, impact, and charm checks, so a Meteor Charm can still cancel it and a Charm of Protection can still divert it.

## Building

```powershell
.\gradlew.bat build
```

The mod jar is written to `build/libs/`.

# Random NTM Additions

<img width="128" height="128" alt="ntmadditions" src="https://github.com/user-attachments/assets/09928283-2979-41e2-98df-a3bc007c0de1" />

A small NeoForge 1.21.1 addon for [NTM Continuum](https://github.com/Warfactory-Official/NTM-Continuum) and [Golem Core](https://github.com/Warfactory-Official/Golem-Core).

Random NTM Additions adds random things we wanted in "NTM: Continuum" for our server.

**Version 0.3.0.1**

## What it adds

### Curios charm variants

Equippable Curios versions of NTM's two armor-insert charms:

- **Meteor Charm (Curio)** — stops meteor strikes aimed at the wearer and negates broadcaster damage.
- **Charm of Protection (Curio)** — diverts meteor strikes, prevents their block damage, and halves broadcaster damage.

Both charms fit in any Curios slot. The addon also supplies a fallback charm slot and the player slot assignments Curios 9.5.1 expects, so the slots appear even on a fresh install.

The original armor-insert `hbm:meteor_charm` and `hbm:protection_charm` are untouched.

### Ward blocks

Two placeable ward blocks:

- **Meteor Ward** — stops meteor strikes whose actual spawn point is inside its radius, and negates broadcaster damage for players inside the radius.
- **Protection Ward** — diverts meteor strikes whose actual spawn point is inside its radius so they spawn safe and cannot destroy blocks, and halves broadcaster damage for players inside the radius.

Right-click either ward to cycle its radius through **50, 100, 150, 200, and 250 blocks**, wrapping back to 50 after 250. The new radius is shown in the action bar, stored in the block entity, and applied immediately.

Wards act on the meteor's spawn point, not the targeted player's position. NTM offsets meteor spawns by up to 100 blocks from the target, so a 50-block ward does not affect a meteor that spawns 75 blocks away even if the targeted player is near the ward.

Loaded wards are indexed server-side. A ward stops affecting meteors when its block entity is removed or its chunk unloads, and overlapping wards extend the protected area.

### Dutch Air Raid siren cassette

Adds a new cassette track for HBM's Machine Siren:

- Item: **Dutch Air Raid Siren Track** (`ntmadditions:cassette_dutch_air_raid`)
- Track/sound: `ntmadditions:dutch_air_raid`
- Sound file: `dutchairraid.ogg`, streamed
- Loop type: `LOOP`
- Range/volume: 500

Insert the cassette into an HBM Machine Siren to play the Dutch air raid siren. The track is registered through NTM's public cassette API and uses HBM's normal siren distance fading. No newer HBM version is required; NTM Continuum 1.0.3 already exposes the needed API.

### Fluid tank safety

Machine fluid tanks no longer rupture when storing highly corrosive fluids. The corrosive-fluid branch that sets the tank to its exploded state is suppressed, so the tank keeps functioning instead of leaking/destroying itself.

The antimatter rupture path is unchanged. Steel barrels still convert to corroded barrels when exposed to highly corrosive fluids; this change only affects the machine fluid tank's corrosive rupture.

### Steel pipe impact sound

Dropped steel pipes (`hbm:pipes_steel`) play a metal pipe falling sound when they first land. The sound plays at the pipe's position with a modest volume and fades out over roughly 24 blocks instead of playing globally.

### Command

`/ntmadditions meteor [targets]` (permission level 2) calls NTM's natural meteor strike path for the targeted players. With no target it strikes the executing player. It uses NTM's normal spawn height, random offset, fall, impact, and charm/ward checks.

## Requirements

| Dependency | Version |
| --- | --- |
| Minecraft | 1.21.1 |
| NeoForge | 21.1.248+ |
| NTM Continuum (`hbm`) | 1.0.3+ |
| Golem Core (`golemcore`) | 1.0.3+ |
| Curios (`curios`) | 9.5.1+1.21.1 |

## How it works

- Both charm items implement Curios' `ICurioItem` and are added to the `curios:curio` item tag, so they fit in any Curios slot.
- The addon ships a `charm` slot definition under its own namespace. Curios merges it with the built-in charm slot when one exists, or supplies the slot when it does not.
- Curios 9.5.1 does not assign any slots to players by default, so the addon ships `data/ntmadditions/curios/entities/player.json` to assign the standard slots (`head`, `hands`, `charm`, `ring`, `belt`, `back`, `necklace`, `bracelet`, `body`, and `curio`) to `minecraft:player`.
- A Golem Core `DamageEvents` listener reproduces the broadcaster-damage effects for the charms and wards.
- A Mixin into `com.hbm.handler.BossSpawnHandler` makes the meteor checks see the Curios items and applies ward effects at the meteor's actual spawn point.
- A Mixin into `TileEntityMachineFluidTank.serverTick` suppresses only the highly-corrosive `explode()` call.
- The Dutch Air Raid track is registered through NTM's `ItemCassette.TrackType.register(...)` and uses the custom `ntmadditions:dutch_air_raid` sound event.
- A Mixin into `ItemEntity.tick` detects when a dropped steel pipe lands and plays the positional metal pipe impact sound once.

## Configuration

`config/ntmadditions-common.toml`:

- `enableCuriosCharms` (default `true`) - master switch for the Curios charm items and their effects. Curios slot data remains installed; when disabled, the charms cannot be equipped and grant no effects.
- `enableWards` (default `true`) - master switch for the Meteor Ward and Protection Ward blocks and their protection effects.
- `enableSiren` (default `true`) - master switch for the Dutch Air Raid siren track and its cassette item.
- `enableMeteorCommand` (default `true`) - master switch for the `/ntmadditions meteor` command.
- `localMinePrimeSound` (default `true`) - when enabled, the mine arming sound uses a custom local sound event that fades out over 32 blocks instead of NTM's long-range volume-3 sound. Disabled keeps NTM's original sound and volume. Explosions, damage, block destruction, and radiation are unchanged. The bundled sound is a mono downmix of NTM's `hbm:weapon/fstbmbstart` so it can be attenuated positionally.
- `preventCorrosiveTankRupture` (default `true`) — when enabled, highly corrosive fluids no longer rupture machine fluid tanks. Set to false to restore NTM's original rupture behavior. Antimatter explosions and steel barrel corrosion are unaffected.
- `metalPipeFallingSound` (default `true`) — when enabled, dropped steel pipes play a positional metal pipe falling sound when they land. The sound fades out over roughly 24 blocks and is intentionally kept at a modest volume.

## Recipes

Recipes for the Curios charms, both wards, and the Dutch Air Raid cassette are intentionally not included yet.

## Building

```powershell
.\gradlew.bat build
```

The mod jar is written to `build/libs/`.

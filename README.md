# Random NTM Additions

A small NeoForge 1.21.1 addon for [NTM Continuum](https://github.com/Warfactory-Official/NTM-Continuum) and [Golem Core](https://github.com/Warfactory-Official/Golem-Core).

Random NTM Additions adds random things we wanted in "NTM: Contiuum" for our server.
Currently adds:

Curios-slot versions of NTM's two armor-insert charms:
*   **Meteor Charm (Curio)** - An equippable curio version of the "Meteor Charm"
*   **Charm of Protection (Curio)** - An equippable curio version of the "Charm of Protection"
*   They fit into any curio slot, and if no curio slots are present then a charm slot is added to be used for them.

Two placeable ward blocks:
*   **Meteor Ward** - Entirely stops meteors from spawning within its range
*   **Protection Ward** - Meteors spawned within its range are diverted away, and cannot damage any blocks
*   Their range can be changed from 50 to 250 blocks by right clicking them

Config at `config/ntmadditions-common.toml`:
*   `localMinePrimeSound` (default `true`) - when enabled, the mine arming/priming sound (beep) uses a custom local sound event that fades out over 32 blocks instead of NTM's default long-range sound. Disabled keeps NTM's original sound and volume. (The beep is annoying to hear when loading chunks).

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
- A small Mixin into `com.hbm.handler.BossSpawnHandler#strike` makes the meteor checks see the Curios items too, and applies ward effects at the meteor's actual spawn point.

## Wards

Both wards are block entities with an independently adjustable radius. Right-click a ward to cycle its radius through **50, 100, 150, 200, and 250 blocks**, wrapping back to 50 after 250. The new radius is shown in the action bar, stored in the block entity, and applied immediately.

Wards act on the meteor's actual spawn point, not the targeted player's position. NTM offsets meteor spawns by up to 100 blocks from the target, so a ward only affects a meteor if its spawn point is inside the radius. For example, a 50-block ward does not stop a meteor that spawns 75 blocks away, even if the targeted player is near the ward.

- **Meteor Ward** — discards meteor strikes whose spawn point is inside its radius, and negates broadcaster damage for players inside the radius.
- **Protection Ward** — diverts meteor strikes whose spawn point is inside its radius so they spawn safe and do not destroy blocks, and halves broadcaster damage for players inside the radius.

Loaded wards are indexed server-side. A ward stops affecting meteors when its block entity is removed or its chunk unloads, and overlapping wards simply extend the protected area.

## Commands

`/ntmadditions meteor [targets]` (permission level 2) calls NTM's natural meteor strike path for the targeted players. With no target it strikes the executing player. It uses NTM's normal spawn height, random offset, fall, impact, and charm checks, so a Meteor Charm can cancel it, a Meteor Ward can cancel it if the meteor's spawn point is in range, a Charm of Protection can divert it, and a Protection Ward can divert it if the meteor's spawn point is in range.

## Configuration

`config/ntmadditions-common.toml`:

- `localMinePrimeSound` (default `true`) - when enabled, the mine arming sound uses a custom local sound event that fades out over 32 blocks instead of NTM's long-range volume-3 sound. Disabled keeps NTM's original sound and volume. Explosions, damage, block destruction, and radiation are unchanged. The bundled sound is a mono downmix of NTM's `hbm:weapon/fstbmbstart` so it can be attenuated positionally.

## Building

```powershell
.\gradlew.bat build
```

The mod jar is written to `build/libs/`.

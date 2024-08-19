# ChangeRaidWavesDifficulty

### This is a pretty simple forge mod that lets you change the raid waves number tied to difficulty, regardless of the server's actual difficulty.
** **

## Config


##### Config file should automatically reload on change. 

*Example & default Config, located under file `/world/serverconfig/changeraidwavesdifficulty-server.toml`*

```toml
# Difficulty for the raid waves to use.
# Use -1 to use the current minecraft difficulty.
#Range: -1 ~ 3
raidDifficulty = -1
```
- ##### `raidDifficulty = -1` || `/raidwaves setdifficulty default`
  ###### - Disables the mod and uses the current difficulty setting for raid waves counts.
- ##### `raidDifficulty = 0` || `/raidwaves setdifficulty peaceful`
  ###### - Sets raids waves counts to the peaeceful difficulty, AKA 0, resulting in raids ending after the inital bossbar load with the "victory!" outcome.
- ##### `raidDifficulty = 1` || `/raidwaves setdifficulty easy`
  ###### - Sets raids waves counts to the normal difficulty, AKA 3.
- ##### `raidDifficulty = 2` || `/raidwaves setdifficulty normal`
  ###### - Sets raids waves counts to the normal difficulty, AKA 5.
- ##### `raidDifficulty = 3` || `/raidwaves setdifficulty hard`
  ###### - Sets raids waves counts to the hard difficulty, AKA 7.

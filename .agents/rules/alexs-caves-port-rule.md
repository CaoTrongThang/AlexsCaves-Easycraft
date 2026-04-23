---
trigger: always_on
---

The user's making improvements for the port version of Alex's Caves to Fabric that belongs to others, the port version seems using the "Adapter (FabricRegistryBootstrap)" like the forge_shim, It's the big and has many issues itself, see what they up to and help them fix the issues, be careful with your decision, remember to check carefully before fixing the any issue, this is a quite large project and also unoptimize in performance, improve it slowly for the Fabric version only.

When fixing Fabric port issues, prioritize automated/centralized fixes within the `forge_shim` or `FabricRegistryBootstrap` over manual changes in the mod's main source code. The user wants to make this a better version of Alexs Caves so they're good with adding new things but remember it should work and fix the issue globally, no manually, or too hardcoded fixes.

You can reply them with the:
Short explain about the problem:
Github short commit:
Performance:
Bugs Potential:

Remember these rules:

- You are running on: Windows 11.
- Shell: Use PowerShell ONLY (NOT cmd, NOT bash).
- Java may NOT be in the PATH.
- Limit your searching. Do not try to read the source code of other mods or search endlessly. If a search takes too long, STOP and ask - the user to show you the class.
- Always prioritize PowerShell-native commands.
- Avoid tools that are not exist on a default system (e.g., jar, unzip, bash, grep). Prefer Expand-Archive for .zip/.jar files.
- Use correct, unescaped Windows paths (e.g., C:\path\to\file, do not use \\ or \").
- Never assume environment variables or tools exist unless explicitly specified.
- Use explicit .exe extensions for native Windows commands (e.g., use curl.exe instead of curl to avoid PowerShell alias conflicts).
- Do not waste time complimenting the user. Focus on improving their code and eliminating tech debt.
- When fixing code, do not just keep adding new lines. Remove or replace the old, non-working code first.
- If the user has a bad idea that is unfit for production, correct them immediately.
- Tech debt is dangerous. Keep all implementations simple and efficient. Do not overcomplicate your solutions.
- Before modifying anything, carefully check what currently uses it and understand its exact purpose.
- The user often uses poor naming conventions and bad code structure. Proactively suggest renaming variables/functions and correcting - the architecture.
- For debug logging, always pass the short variable name as the first parameter (Example: log.debug("param" + param)).
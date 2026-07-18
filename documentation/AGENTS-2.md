# Project Context: Supply Chain Logistics Library

## Thesis Overview
**Topic:** Scalable Modular Architectures and Formal Verification in Logistics Systems.
**Primary Goal:** To build a robust logistics library that demonstrates clean architectural patterns.
**Verification Scope:** Only critical "core" sections will be formally verified using JML and the KeY Tool. The rest of the project will focus on modularity and industry-standard practices.

## Technical Constraints & Guidelines
1. **Language:** Java (Modern versions).
2. **Libraries:**
   - **Allowed:** Java Standard Library, Google Guava, Apache Commons.
   - **Usage Rule:** Use functions that are compatible with JML specifications. For parts intended for KeY verification, stick to simpler, deterministic logic.
3. **Formal Verification (JML/KeY):**
   - **Selective Verification:** Not all code needs to be verified. Focus verification on state transitions, inventory counts, and core business rules.
   - Methods in "Verified Zones" must have clear Pre/Post-conditions.
4. **Design Patterns & OOP:**
   - **Strategy, Observer, Inheritance.**
   - Demonstrate **Inheritance** through specialized types (e.g., `InternationalShipment`), ensuring behavioral subtyping in verified sections.

## Architectural Map
- `logistics.model`: Core entities.
- `logistics.core`: Logic & Strategies.
- `logistics.observer`: Monitoring.
- `client`: Scenarios.

## Future Feature Requirements
1. Maintain decoupling even when using 3rd party libraries.
2. Clearly mark sections intended for formal verification.
3. Use Guava/Commons to simplify non-verified infrastructure (e.g., logging, string utils, collection helpers).
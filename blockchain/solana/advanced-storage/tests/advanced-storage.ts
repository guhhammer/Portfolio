import * as anchor from "@coral-xyz/anchor";
import { Program } from "@coral-xyz/anchor";
import { AdvancedStorage } from "../target/types/advanced_storage";
import { assert } from "chai";

describe("advanced_storage", () => {
  // Configure the client to use the local cluster.
  const provider = anchor.AnchorProvider.env();
  anchor.setProvider(provider);

  const program = anchor.workspace.advanced_storage as Program<AdvancedStorage>;

  let callerStorage: anchor.web3.PublicKey;

  before(async () => {
    // Derive PDA (your program's storage account)
    [callerStorage] = anchor.web3.PublicKey.findProgramAddressSync(
      [Buffer.from("caller"), provider.wallet.publicKey.toBuffer()],
      program.programId
    );
  });

  it("Should set data on-chain and fetch it back", async () => {
    // Call the set instruction
    const tx = await program.methods
      .set("157", "int")
      .accounts({
        callerStorage,
        user: provider.wallet.publicKey,
        systemProgram: anchor.web3.SystemProgram.programId,
      })
      .rpc();

    console.log("Set Tx Signature:", tx);

    // Fetch account data
    const accountData = await program.account.storage.fetch(callerStorage);

    console.log("Fetched Account Data:", accountData);

    // Assertions
    assert.equal(accountData.data, "157", "Stored data mismatch");
    assert.equal(accountData.convert, "int", "Stored convert type mismatch");
  });
});

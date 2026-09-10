import * as anchor from "@coral-xyz/anchor";
import { Program } from "@coral-xyz/anchor";
import { ArmstrongNumber } from "../target/types/armstrong_number";
import { assert } from "chai";

describe("Armstrong Number", () => {

    const provider =  anchor.AnchorProvider.env();
    anchor.setProvider(provider);

    const program = anchor.workspace.armstrong_number as Program<ArmstrongNumber>;

    let result: anchor.web3.PublicKey;

    before(async () => {

        [result] = anchor.web3.PublicKey.findProgramAddressSync(
            [Buffer.from("result"), provider.wallet.publicKey.toBuffer()],
            program.programId
        );

    });

    it("should check if number is armstrong number", async () => {
    
        const tx = await program.methods
        .isArmstrongNumber(157)
        .accounts({
            result,
            user: provider.wallet.publicKey,
            systemProgram: anchor.web3.SystemProgram.programId,

        })
        .rpc();

        console.log("Set Tx Signature: ", tx);

        const accountData = await program.account.armstrongResult.fetch(result);

        console.log("Fetched Account Data: ", accountData);

        assert.equal(accountData.isArmstrong, false, "stored data mismatch");

        const tx2 = await program.methods
        .isArmstrongNumber(153)
        .accounts({
            result,
            user: provider.wallet.publicKey,
            systemProgram: anchor.web3.SystemProgram.programId,

        })
        .rpc();

        console.log("Set Tx Signature: ", tx2);

        const accountData2 = await program.account.armstrongResult.fetch(result);

        console.log("Fetched Account Data: ", accountData2);

        assert.equal(accountData2.isArmstrong, true, "stored data mismatch");

    });

});
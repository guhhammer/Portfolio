use anchor_lang::prelude::*;

declare_id!("5ySaYqh4WwZMoNiVmW6B8Hb4Kb1AwXABNaXpR4pHpQR2");

#[program]
pub mod helloworld {
    use super::*;

    pub fn initialize(ctx: Context<Initialize>) -> Result<()> {
        msg!("Greetings from: {:?}", ctx.program_id);
        Ok(())
    }
}

#[derive(Accounts)]
pub struct Initialize {}

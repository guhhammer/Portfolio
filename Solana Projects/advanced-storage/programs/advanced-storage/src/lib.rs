use anchor_lang::prelude::*;

declare_id!("AxE5pTKRzdBg6Tu8vvB3J98hf5Fmx3iwRatcG82DTNJt");

#[program]
pub mod advanced_storage {
    use super::*;

    pub fn set(ctx: Context<Holder>, data: String, convert: String) -> Result<()> {
        ctx.accounts.caller_storage.data = data;
        ctx.accounts.caller_storage.convert = convert;
        Ok(())
    }
    
}

#[derive(Accounts)]
pub struct Holder<'info> {
    #[account(
        init, 
        seeds = [b"caller", user.key().as_ref()], 
        bump, 
        payer = user, 
        space = 8 + 4 + 32 + 4 + 32,
    )]
    pub caller_storage: Account<'info, Storage>,
    #[account(mut)]
    pub user: Signer<'info>,
    pub system_program: Program<'info, System>,
}

#[account]
pub struct Storage {
    pub data: String,
    pub convert: String,
}

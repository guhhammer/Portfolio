use anchor_lang::prelude::*;

declare_id!("H5c2LFtjH5Sw5Yp2j7Z2RTRhJQT2dxXu4UpyBAiRBvem");

#[program]
pub mod armstrong_number {
    use super::*;

    pub fn is_armstrong_number(ctx: Context<CheckArmstrong>, n: u32) -> Result<()> {

        let mut num: u32 = n;
        let mut digits: Vec<u32> = Vec::new();

        while num > 0 { digits.push(num % 10); num /= 10; }

        let power = digits.len() as u32;
        let mut sum: u32 = 0;

        for &digit in &digits { sum += digit.pow(power); }

        ctx.accounts.result.is_armstrong = sum == n;

        Ok(())

    }

}

#[derive(Accounts)]
pub struct CheckArmstrong<'info> {
    #[account(
        init_if_needed,
        payer = user,
        space = 8 + 1 + 1, // bool + bump
        seeds = [b"result", user.key().as_ref()],
        bump
    )]
    pub result: Account<'info, ArmstrongResult>,

    #[account(mut)]
    pub user: Signer<'info>,

    pub system_program: Program<'info, System>,
}

#[account]
pub struct ArmstrongResult {
    pub is_armstrong: bool,
    pub bump: u8,
}
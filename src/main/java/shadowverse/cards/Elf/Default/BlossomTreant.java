package shadowverse.cards.Elf.Default;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import shadowverse.characters.Elf;

public class BlossomTreant extends CustomCard {
    public static final String ID = "shadowverse:BlossomTreant";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:BlossomTreant");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/BlossomTreant.png";

    public BlossomTreant() {
        super(ID, NAME, IMG_PATH, 2, DESCRIPTION, CardType.ATTACK, Elf.Enums.COLOR_GREEN, CardRarity.COMMON, CardTarget.SELF);
        this.baseBlock = 12;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded){
            upgradeName();
            upgradeBlock(4);
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(abstractPlayer, abstractPlayer, this.block));
        addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new DrawCardNextTurnPower(abstractPlayer, 1), 1));
    }
}

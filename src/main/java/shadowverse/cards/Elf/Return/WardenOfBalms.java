package shadowverse.cards.Elf.Return;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import shadowverse.cards.Witch.AbstractAccelerateCard;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Elf;

public class WardenOfBalms extends AbstractAccelerateCard {
    public static final String ID = "shadowverse:WardenOfBalms";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:WardenOfBalms");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/WardenOfBalms.png";

    public WardenOfBalms() {
        super(ID, NAME, IMG_PATH, 3, DESCRIPTION, CardType.ATTACK, Elf.Enums.COLOR_GREEN, CardRarity.UNCOMMON, CardTarget.SELF, 0, CardType.POWER);
        this.baseBlock = 12;
        this.baseMagicNumber = 6;
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(AbstractShadowversePlayer.Enums.CONDEMNED);
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(4);
        }
    }

    @Override
    public void baseUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, this.block));
        addToBot(new GainBlockAction(p, this.block));
        addToBot(new DamageAllEnemiesAction(p, DamageInfo.createDamageMatrix(this.magicNumber, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE, true));
        addToBot(new DamageAllEnemiesAction(p, DamageInfo.createDamageMatrix(this.magicNumber, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE, true));
    }

    @Override
    public void accUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new DrawCardNextTurnPower(p, 1), 1));
    }


    public AbstractCard makeCopy() {
        return new WardenOfBalms();
    }
}

package shadowverse.cards.Neutral.Curse;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowverse.characters.AbstractShadowversePlayer;

public class DivineMinister extends CustomCard {
    public static final String ID = "shadowverse:DivineMinister";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:DivineMinister");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/DivineMinister.png";

    public DivineMinister() {
        super(ID, NAME, IMG_PATH, 2, DESCRIPTION, CardType.CURSE, CardColor.CURSE, CardRarity.SPECIAL, CardTarget.SELF);
        this.baseBlock = 12;
        this.exhaust = true;
    }

    @Override
    public void upgrade() {
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        addToBot(new SFXAction("DivineMinister"));
        addToBot(new GainBlockAction(p,this.block));
        if (AbstractDungeon.player instanceof AbstractShadowversePlayer){
            ((AbstractShadowversePlayer)AbstractDungeon.player).lainaCount++;
        }
    }


    @Override
    public AbstractCard makeCopy(){
        return new DivineMinister();
    }
}

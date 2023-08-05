package shadowverse.cards.Necromancer.Default;


import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import shadowverse.Shadowverse;
import shadowverse.action.ReanimateAction;
import shadowverse.cards.Witch.AbstractAccelerateCard;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Necromancer;


public class AdherentOfScream
        extends AbstractAccelerateCard {
    public static final String ID = "shadowverse:AdherentOfScream";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:AdherentOfScream");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/AdherentOfScream.png";

    public AdherentOfScream() {
        super(ID, NAME, IMG_PATH, 2, DESCRIPTION, CardType.ATTACK, Necromancer.Enums.COLOR_PURPLE, CardRarity.COMMON, CardTarget.SELF, 0, CardType.SKILL);
        this.baseBlock = 6;
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(3);
        }
    }

    @Override
    public void update() {
        if (AbstractDungeon.currMapNode != null && (AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT &&
                Shadowverse.Accelerate(this)) {
            setCostForTurn(0);
            this.type = CardType.SKILL;
        } else {
            if (this.type == CardType.SKILL) {
                setCostForTurn(2);
                this.type = CardType.ATTACK;
            }
        }
        super.update();
    }

    @Override
    public void baseUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("AdherentOfScream"));
        addToBot(new GainBlockAction(p, this.block));
        if (this.costForTurn > 0) {
            addToBot(new ReanimateAction(1));
            addToBot(new ReanimateAction(1));
        }
    }

    @Override
    public void accUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("AdherentOfScream_Acc"));
        addToBot(new ReanimateAction(1));
    }

    public AbstractCard makeCopy() {
        return new AdherentOfScream();
    }
}


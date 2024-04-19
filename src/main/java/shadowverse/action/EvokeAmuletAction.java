package shadowverse.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import shadowverse.cards.AbstractNoCountDownAmulet;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.orbs.AmuletOrb;

import java.util.ArrayList;
import java.util.HashMap;

public class EvokeAmuletAction extends AbstractGameAction {
    public static final String[] TEXT = (CardCrawlGame.languagePack.getUIString("shadowverse:ReduceCountDown")).TEXT;

    private AbstractPlayer p = AbstractDungeon.player;
    private ArrayList<AbstractOrb> cannotChose = new ArrayList<>();
    private HashMap<AbstractCard,Integer> map = new HashMap<>();
    private boolean retrieveCard = false;

    public EvokeAmuletAction(){
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = 0.25F;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.p.orbs.size() == 0) {
                this.isDone = true;
                return;
            }
            for (AbstractOrb o : this.p.orbs) {
                if (!(o instanceof AmuletOrb)) {
                    this.cannotChose.add(o);
                }else {
                    if (((AmuletOrb) o).amulet instanceof AbstractNoCountDownAmulet){
                        this.cannotChose.add(o);
                    }
                }
            }
            if (this.cannotChose.size() == this.p.orbs.size()) {
                this.isDone = true;
                return;
            }
            AbstractDungeon.cardRewardScreen.customCombatOpen(generateCardChoices(), TEXT[0], false);
            tickDuration();
            return;
        }
        if (!this.retrieveCard) {
            if (AbstractDungeon.cardRewardScreen.discoveryCard != null) {
                AbstractCard disCard = AbstractDungeon.cardRewardScreen.discoveryCard;
                AbstractOrb orb = p.orbs.get(map.get(disCard));
                if (orb instanceof AmuletOrb &&!((AmuletOrb) orb).amulet.hasTag(AbstractShadowversePlayer.Enums.MINION)){
                    orb.passiveAmount = 0;
                    AbstractDungeon.actionManager.addToBottom( new StasisEvokeIfRoomInHandAction((AmuletOrb) orb));
                }
                AbstractDungeon.cardRewardScreen.discoveryCard = null;
            }
            this.retrieveCard = true;
        }
        tickDuration();
    }

    private ArrayList<AbstractCard> generateCardChoices()  {
        ArrayList<AbstractCard> derp = new ArrayList<>();
        for (int i = 0;i<p.orbs.size();i++){
            AbstractOrb o = p.orbs.get(i);
            if (o instanceof AmuletOrb){
                AbstractCard c = ((AmuletOrb) o).amulet;
                this.map.put(c,i);
                if (!(c instanceof AbstractNoCountDownAmulet)){
                    derp.add(c);
                }
            }
        }
        return derp;
    }
}

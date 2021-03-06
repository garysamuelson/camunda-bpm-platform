package org.camunda.bpm.engine.impl.json;

import org.camunda.bpm.engine.impl.cmd.AbstractProcessInstanceModificationCommand;
import org.camunda.bpm.engine.impl.cmd.ActivityAfterInstantiationCmd;
import org.camunda.bpm.engine.impl.cmd.ActivityBeforeInstantiationCmd;
import org.camunda.bpm.engine.impl.cmd.ActivityCancellationCmd;
import org.camunda.bpm.engine.impl.cmd.TransitionInstantiationCmd;
import org.camunda.bpm.engine.impl.util.JsonUtil;
import org.camunda.bpm.engine.impl.util.json.JSONObject;

public class ModificationCmdJsonConverter extends JsonObjectConverter<AbstractProcessInstanceModificationCommand> {

  public static final ModificationCmdJsonConverter INSTANCE = new ModificationCmdJsonConverter();

  public static final String START_BEFORE = "startBeforeActivity";
  public static final String START_AFTER = "startAfterActivity";
  public static final String START_TRANSITION = "startTransition";
  public static final String CANCEL_ALL = "cancelAllForActivity";

  @Override
  public JSONObject toJsonObject(AbstractProcessInstanceModificationCommand command) {
    JSONObject json = new JSONObject();

    if (command instanceof ActivityAfterInstantiationCmd) {
      JsonUtil.addField(json, START_AFTER, ((ActivityAfterInstantiationCmd) command).getTargetElementId());
    }
    else if (command instanceof ActivityBeforeInstantiationCmd) {
      JsonUtil.addField(json, START_BEFORE, ((ActivityBeforeInstantiationCmd) command).getTargetElementId());
    }
    else if (command instanceof TransitionInstantiationCmd) {
      JsonUtil.addField(json, START_TRANSITION, ((TransitionInstantiationCmd) command).getTargetElementId());
    }
    else if (command instanceof ActivityCancellationCmd) {
      JsonUtil.addField(json, CANCEL_ALL, ((ActivityCancellationCmd) command).getActivityId());
    }

    return json;
  }

  @Override
  public AbstractProcessInstanceModificationCommand toObject(JSONObject json) {

    if (json.has(START_BEFORE)) {
      return new ActivityBeforeInstantiationCmd(null, json.getString(START_BEFORE));
    }

    if (json.has(START_AFTER)) {
      return new ActivityAfterInstantiationCmd(null, json.getString(START_AFTER));
    }

    if (json.has(START_TRANSITION)) {
      return new TransitionInstantiationCmd(null, json.getString(START_TRANSITION));
    }

    if (json.has(CANCEL_ALL)) {
      return new ActivityCancellationCmd(null, json.getString(CANCEL_ALL));
    }

    return null;
  }

}

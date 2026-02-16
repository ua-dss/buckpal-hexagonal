FROM ubuntu:24.04 


SHELL ["/bin/bash","-lc"]

ARG USERNAME=coder
ARG PROMPT_HOSTNAME=javadev

# Expose the chosen prompt hostname into the container environment
ENV PROMPT_HOSTNAME=${PROMPT_HOSTNAME}

ENV DEBIAN_FRONTEND=noninteractive

# Install required packages, create a non-root user, and clean up apt caches in a single layer
RUN apt-get update \
    && apt-get install -y --no-install-recommends \
        sudo \
        git \
		openjdk-17-jdk \
    && useradd -m -U -s /bin/bash $USERNAME \
    && echo "$USERNAME ALL=(root) NOPASSWD:ALL" > /etc/sudoers.d/$USERNAME \
    && chmod 0440 /etc/sudoers.d/$USERNAME \
    && rm -rf /var/lib/apt/lists/*

# Set a predictable shell for scripts and inner shells
ENV SHELL=/bin/bash


# Create a global profile to show the chosen hostname
RUN cat > /etc/profile.d/${PROMPT_HOSTNAME}.sh <<EOF
# Interactive shells only
case "\$-" in
    *i*)
        if [ -n "\$VIRTUAL_ENV" ]; then
            VE_NAME=\$(basename "\$VIRTUAL_ENV")
            export PS1="(\$VE_NAME) \\\u@${PROMPT_HOSTNAME}:\\\w\\$ "
        else
            export PS1="\\u@${PROMPT_HOSTNAME}:\\w\\$ "
        fi
        ;;
esac
EOF


# Expose home for later steps and switch to the non-root user
ENV HOME=/home/${USERNAME}
USER ${USERNAME}
WORKDIR /home/${USERNAME}
